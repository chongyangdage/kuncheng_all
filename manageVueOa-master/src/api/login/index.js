import request from '../request'

//登录
export function Login(obj) {
  return request({
    url: '/user/login',
    method: 'post',
    params:obj
  })
}

