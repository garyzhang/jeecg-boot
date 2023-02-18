import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '订单编码',
    align:"center",
    dataIndex: 'orderCode'
   },
   {
    title: '下单时间',
    align:"center",
    dataIndex: 'xdDate'
   },
   {
    title: '订单总额',
    align:"center",
    dataIndex: 'money'
   },
   {
    title: '备注',
    align:"center",
    dataIndex: 'remark'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '订单编码',
    field: 'orderCode',
    component: 'Input',
    dynamicDisabled:true,
  },
  {
    label: '下单时间',
    field: 'xdDate',
    component: 'DatePicker',
    componentProps: {
       showTime:true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '订单总额',
    field: 'money',
    component: 'InputNumber',
  },
  {
    label: '备注',
    field: 'remark',
    component: 'Input',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

//子表列表数据
export const cesOrderGoodsColumns: BasicColumn[] = [
   {
    title: '商品名字',
    align:"center",
    dataIndex: 'goodName'
   },
   {
    title: '价格',
    align:"center",
    dataIndex: 'price'
   },
   {
    title: '数量',
    align:"center",
    dataIndex: 'num'
   },
   {
    title: '单品总价',
    align:"center",
    dataIndex: 'zongPrice'
   },
];
//子表表单数据
export const cesOrderGoodsFormSchema: FormSchema[] = [
  // TODO 子表隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false
  },
  {
    label: '商品名字',
    field: 'goodName',
    component: 'Input',
  },
  {
    label: '价格',
    field: 'price',
    component: 'InputNumber',
  },
  {
    label: '数量',
    field: 'num',
    component: 'InputNumber',
  },
  {
    label: '单品总价',
    field: 'zongPrice',
    component: 'InputNumber',
  },
];
//子表列表数据
export const cesOrderCustomerColumns: BasicColumn[] = [
   {
    title: '客户名字',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '客户性别',
    align:"center",
    dataIndex: 'sex_dictText'
   },
   {
    title: '客户生日',
    align:"center",
    dataIndex: 'birthday'
   },
   {
    title: '年龄',
    align:"center",
    dataIndex: 'age'
   },
   {
    title: '常用地址',
    align:"center",
    dataIndex: 'address'
   },
];
//子表表单数据
export const cesOrderCustomerFormSchema: FormSchema[] = [
  // TODO 子表隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false
  },
  {
    label: '客户名字',
    field: 'name',
    component: 'JPopup',
    componentProps: ({ formActionType }) => {
        const {setFieldsValue} = formActionType;
        return{
            setFieldsValue:setFieldsValue,
            code:"tj_user_report",
            fieldConfig: [
                { source: 'realname', target: 'name' },
                { source: 'sex', target: 'sex' },
                { source: 'birthday', target: 'birthday' },
            ],
            multi:true
        }
    },

  },
  {
    label: '客户性别',
    field: 'sex',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"sex"
     },
  },
  {
    label: '客户生日',
    field: 'birthday',
    component: 'DatePicker',
    componentProps: {
       showTime:true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '年龄',
    field: 'age',
    component: 'InputNumber',
  },
  {
    label: '常用地址',
    field: 'address',
    component: 'Input',
  },
];
