<template>
  <div>
    <BasicForm @register="registerForm" ref="formRef"/>
    <!-- 子表单区域 -->
    <a-tabs v-model:activeKey="activeKey" animated  @change="handleChangeTabs">
          <a-tab-pane tab="订单商品" key="cesOrderGoods" :forceRender="true">
            <JVxeTable
              keep-source
              resizable
              ref="cesOrderGoods"
              v-if="cesOrderGoodsTable.show"
              :loading="cesOrderGoodsTable.loading"
              :columns="cesOrderGoodsTable.columns"
              :dataSource="cesOrderGoodsTable.dataSource"
              :height="340"
              :rowNumber="true"
              :rowSelection="true"
              :disabled="formDisabled"
              :toolbar="true"
            />
          </a-tab-pane>
          <a-tab-pane tab="订单客户" key="cesOrderCustomer" :forceRender="true">
            <CesOrderCustomerForm ref="cesOrderCustomerForm" :disabled="formDisabled"></CesOrderCustomerForm>
          </a-tab-pane>

    </a-tabs>

    <div style="width: 100%;text-align: center" v-if="!formDisabled">
      <a-button @click="handleSubmit" pre-icon="ant-design:check" type="primary">提 交</a-button>
    </div>
  </div>
</template>

<script lang="ts">

  import {BasicForm, useForm} from '/@/components/Form/index';
  import { computed, defineComponent, reactive, ref, unref } from 'vue';
  import {defHttp} from '/@/utils/http/axios';
  import { propTypes } from '/@/utils/propTypes';
  import { useJvxeMethod } from '/@/hooks/system/useJvxeMethods';
  import { VALIDATE_FAILED } from '/@/utils/common/vxeUtils';
  import CesOrderCustomerForm from './CesOrderCustomerForm.vue'
  import {getBpmFormSchema,cesOrderGoodsColumns} from '../CesOrderMain.data';
  import {saveOrUpdate,cesOrderGoodsList,cesOrderCustomerList} from '../CesOrderMain.api';

  export default defineComponent({
    name: "CesOrderMainForm",
    components:{
      BasicForm,
      CesOrderCustomerForm,
    },
    props:{
      formData: propTypes.object.def({}),
      formBpm: propTypes.bool.def(true),
    },
    setup(props){
      const [registerForm, { setFieldsValue, setProps }] = useForm({
        labelWidth: 150,
        schemas: getBpmFormSchema(props.formData),
        showActionButtonGroup: false,
        baseColProps: {span: 24}
      });

      const formDisabled = computed(()=>{
        if(props.formData.disabled === false){
          return false;
        }
        return true;
      });

      const refKeys = ref(['cesOrderGoods', 'cesOrderCustomer', ]);
      const activeKey = ref('cesOrderGoods');
      const cesOrderGoods = ref();
      const cesOrderCustomerForm = ref();
      const tableRefs = {cesOrderGoods, };
      const cesOrderGoodsTable = reactive({
        loading: false,
        dataSource: [],
        columns:cesOrderGoodsColumns,
        show: false
      })

      const [handleChangeTabs,handleSubmit,requestSubTableData,formRef] = useJvxeMethod(requestAddOrEdit,classifyIntoFormData,tableRefs,activeKey,refKeys,validateSubForm);

      function classifyIntoFormData(allValues) {
        let main = Object.assign({}, allValues.formValue)
        return {
          ...main, // 展开
          cesOrderGoodsList: allValues.tablesValue[0].tableData,
          cesOrderCustomerList: cesOrderCustomerForm.value.getFormData(),
        }
      }
      //校验所有一对一子表表单
      function validateSubForm(allValues){
        return new Promise((resolve, _reject)=>{
          Promise.all([
            cesOrderCustomerForm.value.validateForm(1),
          ]).then(() => {
            resolve(allValues)
          }).catch(e => {
            if (e.error === VALIDATE_FAILED) {
              // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
              activeKey.value = e.index == null ? unref(activeKey) : refKeys.value[e.index]
            } else {
              console.error(e)
            }
          })
        })
      }

      //表单提交事件
      async function requestAddOrEdit(values) {
        await saveOrUpdate(values, true);
      }

      const queryByIdUrl = '/order/cesOrderMain/queryById';
      async function initFormData(){
        let params = {id: props.formData.dataId};
        const data = await defHttp.get({url: queryByIdUrl, params});
        //设置表单的值
        await setFieldsValue({...data});
        requestSubTableData(cesOrderGoodsList, {id: data.id}, cesOrderGoodsTable, ()=>{
          cesOrderGoodsTable.show = true;
        });
        cesOrderCustomerForm.value.initFormData(cesOrderCustomerList, data.id);
        //默认是禁用
        await setProps({disabled: formDisabled.value})
      }

      initFormData();

      return {
        registerForm,
        formDisabled,
        formRef,
        handleSubmit,
        activeKey,
        handleChangeTabs,
        cesOrderGoods,
        cesOrderCustomerForm,
        cesOrderGoodsTable,
      }
    }
  });
</script>