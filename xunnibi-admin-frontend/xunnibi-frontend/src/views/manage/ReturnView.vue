<template>
    <div class="container">

        <h2>归还设备</h2>
        <form @submit.prevent="returnEquipment" class="form">
            <div class="form-group">
                <label>类型1:</label>
                <select v-model="returnRequest.type1" required>
                    <option value="1">工位</option>
                    <option value="2">打印设备</option>
                    <option value="3">摄像设备</option>
                    <option value="4">场地</option>
                </select>
            </div>

            <div class="form-group">
                <label>具体类型:</label>
                <input v-model="returnRequest.type2" placeholder="请输入具体类型" required />
            </div>

            <div class="form-group">
                <label>租借天数:</label>
                <input v-model="returnRequest.rentalDays" type="number" placeholder="天数" required />
            </div>

            <div class="form-group">
                <label>团队ID:</label>
                <input v-model="returnRequest.teamId" type="number" placeholder="团队ID" required />
            </div>

            <button type="submit" class="button">归还</button>
        </form>
        <p class="message">{{ returnMessage }}</p>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    data() {
        return {
            rentalRequest: {
                type1: '',
                type2: '',
                rentalDays: '',
                teamId: ''
            },
            returnRequest: {
                type1: '',
                type2: '',
                rentalDays: '',
                teamId: ''
            },
            rentMessage: '',
            returnMessage: ''
        };
    },
    methods: {
        async rentEquipment() {
            try {
                const response = await axios.post('http://localhost:8080/consumer/rent', this.rentalRequest)
                this.rentMessage = response.data;
            } catch (error) {
                this.rentMessage = error.response?.data || "租借设备失败。";
            }
        },
        async returnEquipment() {
            try {
                const response = await axios.post('http://localhost:8080/consumer/return', this.returnRequest);
                this.returnMessage = response.data;
            } catch (error) {
                this.returnMessage = error.response?.data || "归还设备失败。";
            }
        }
    }
};
</script>

<style scoped>
.container {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
    font-family: Arial, sans-serif;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}

h2 {
    color: #333;
    font-size: 1.5em;
    margin-bottom: 10px;
}

.form {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
}

.form-group {
    margin-bottom: 15px;
}

label {
    display: block;
    margin-bottom: 5px;
    color: #555;
    font-weight: bold;
}

input,
select {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 1em;
    box-sizing: border-box;
}

.button {
    width: 100%;
    padding: 10px;
    background-color: #4CAF50;
    color: #fff;
    border: none;
    border-radius: 4px;
    font-size: 1em;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.button:hover {
    background-color: #45A049;
}

.message {
    color: #333;
    font-size: 1em;
    text-align: center;
    margin-top: 10px;
}
</style>