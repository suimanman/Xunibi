import operate from './operate.js'
const http = (options) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: operate.api() + options.url, //接口地址：前缀+方法中传入的地址
			method: options.method || 'GET', //请求方法：传入的方法或者默认是“GET”
			data: options.data || {}, //传递参数：传入的参数或者默认传递空集合
			header: {
			   'Authorization':"token", //自定义请求头信息
			},
			success: (res) => {
				if(res.data.code!==200){ //自定请求失败的情况
					uni.showToast({
					    title:res.data.msg,
					    icon: 'none'
					});
				}
				resolve(res.data)//成功
			},
			// 这里的接口请求，如果出现问题就输出接口请求失败
			fail: (err) => {
				uni.showToast({
				    title: "" + err.msg,
				    icon: 'none'
				});
				reject(err)
			}
		})
	})
}
export default http
