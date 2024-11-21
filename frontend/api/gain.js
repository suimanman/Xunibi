import request from "@/utils/request";

export function achievementAward(description) {
	return request({
		url: '/gain/achievementSubmit',
		header: {
			isToken: true
		},
		method: 'post',
		data: description
	})
}