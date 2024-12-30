<template>
    <div ref="chart" class="numsChart-container"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
    props: {
        // 接收图表的标题、Y轴数据和系列数据
        chartTitle: {
            type: String,
            default: "各团队人数汇总"
        },
        teamNumsData: {
            type: Array,
            required: true
        },
    },
    data() {
        return {
            chart: null
        };
    },
    mounted() {
        this.initChart();
    },
    watch: {
        // 当传入的数据发生变化时，更新图表
        teamNumsData: "updateChart",
    },
    methods: {
        initChart() {
            const chartDom = this.$refs.chart;
            this.chart = echarts.init(chartDom);
            this.updateChart();
        },
        updateChart() {
            // 对 teamNumsData 按人数排序

            const sortedData = [...this.teamNumsData].sort((a, b) => a.personNum - b.personNum);

            // 提取团队名称和人数数据
            const names = sortedData.map(item => item.name);
            const personNums = sortedData.map(item => item.personNum);

            const option = {
                title: {
                    text: this.chartTitle
                },
                tooltip: {
                    trigger: "axis",
                    axisPointer: {
                        type: "shadow"
                    }
                },
                legend: {},
                grid: {
                    left: "3%",
                    right: "4%",
                    bottom: "3%",
                    containLabel: true
                },
                xAxis: {
                    type: "value",
                    boundaryGap: [0, 0.01]
                },
                yAxis: {
                    type: "category",
                    data: names
                },
                series: [
                    {
                        name: "人数",
                        type: "bar",
                        data: personNums, // 使用人数数据
                        itemStyle: { color: "#c23531" }
                    }
                ]
            };

            if (this.chart) {
                this.chart.setOption(option);
            }
        }
    },
    beforeDestroy() {
        // 销毁图表实例以释放内存
        if (this.chart) {
            this.chart.dispose();
        }
    }
};
</script>

<style scoped>
.numsChart-container {
    width: 600px;
    height: 500px;
}
</style>