import request from '../request'

//登录
export function findPeople() {
  return request({
    url: '/user/findPeople',
    method: 'post',

  })
}

//删除人员
export function delePeople(obj) {
  return request({
    url: '/user/delePeople',
    method: 'post',
    params:obj
  })
}