import http from '../common/request.js'
export function getUserInfo() {
  return http({
    url: '/getUserInfo',
    method: 'post',
  })
}

