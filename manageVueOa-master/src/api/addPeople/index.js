import request from '../request'

//添加人员
export function addPeople(obj) {
  return request({
    url: '/user/insertPeople',
    method: 'post',
    params:obj
  })
}



