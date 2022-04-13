import axios from 'axios'
import { Notification, MessageBox, Message } from 'element-ui'

import { getToken } from './auth'
import errorCode from './errorCode'

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8';


// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: "http://localhost:8080/",
  // baseURL: process.env.VUE_APP_IFRAME_PREFIX,
  // 超时
  timeout: 300000
})
// request拦截器
service.interceptors.request.use(config => {
  // 是否需要设置 token
  // const isToken = (config.headers || {}).isToken === false
  // if (getToken() && !isToken) {
  //   config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
  // }
  

  return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {
    // 未设置状态码则默认成功状态
    const code = res.data.code || '0';
    // 获取错误信息
    const message = res.data.msg || errorCode[code] || errorCode['default']
    if (res.data.exception) {
      let err = res.data.exception
      if (err.code === '12101001') {
        MessageBox.confirm(
          '登录状态已过期，请重新登录',
          '系统提示',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
       
          sessionStorage.setItem('userInfo', '')
        })
      }
    } else {
      if (code !== '0') {
        Message({
          message: message,
          type: 'error'
        })
        // Notification.error({
        //   title: message
        // })
        return Promise.reject('error')
      } else {
        return res.data
      }
    }
  },
  error => {
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
