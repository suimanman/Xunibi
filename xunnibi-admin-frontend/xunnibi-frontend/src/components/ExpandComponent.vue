<template>
    <div ref="ExpandChart" style="width: 100%;height: 600px;"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
    props: {
        chartData: {
            type: Array,
            required: true,
        },
    },
    data() {
        return {
            ExpandChart: null,
        };
    },
    mounted() {
        // // window.onload = () => {
        // //     this.initChart(); // 在窗口完全加载后初始化图表
        // // };
        this.$nextTick(() => {
            this.initChart(); // 等待 DOM 渲染完成后初始化图表
        });
        window.addEventListener("resize", this.resizeChart); // 窗口大小变化时调整图表尺寸

    },
    beforeDestroy() {
        window.removeEventListener("resize", this.resizeChart); // 防止内存泄漏
    },
    watch: {
        chartData: {
            immediate: true,
            handler(newValue) {
                this.updateChart(newValue);
            },
        },
    },
    methods: {
        resizeChart() {
            if (this.ExpandChart) {
                this.ExpandChart.resize(); // 调整图表尺寸
            }
        },
        initChart() {
            const chartDom = this.$refs.ExpandChart;
            this.ExpandChart = echarts.init(chartDom);
            const series = this.chartData.slice(1).map((row) => ({
                name: row[0],  // 系列名称，使用每行的第一个元素作为名称
                type: "line",
                data: row.slice(1),  // 数据，使用每行的剩余元素作为数据
                smooth: true,
                seriesLayoutBy: "row",
                emphasis: { focus: "series" },
            }));

            series.push({
                type: "pie",
                id: "pie",
                radius: ["10", "30%"],
                center: ["50%", "25%"],
                emphasis: { focus: "self" },
                label: { formatter: "{b}: {@currentMonth} ({d}%)" },
                encode: { itemName: "product", value: "currentMonth", tooltip: "currentMonth" },
                itemStyle: {
                    borderWidth: 5,  // Add a border width to create the gap
                    borderColor: "#fff"  // White border to create a clear separation between slices
                }
            });

            const option = {
                legend: {},
                tooltip: {
                    trigger: "axis",
                    showContent: false,
                },
                dataset: {
                    source: this.chartData,
                },
                xAxis: {
                    type: "category",
                    data: this.chartData[0].slice(1),
                },
                yAxis: { gridIndex: 0 },
                grid: { top: "55%" },
                series
            };

            this.ExpandChart.setOption(option);

            this.ExpandChart.on("updateAxisPointer", (event) => {
                const xAxisInfo = event.axesInfo[0];
                if (xAxisInfo) {
                    const dimension = xAxisInfo.value + 1;
                    this.ExpandChart.setOption({
                        series: {
                            id: "pie",
                            label: { formatter: "{b}: {@[" + dimension + "]} ({d}%)" },
                            encode: { value: dimension, tooltip: dimension },
                        },
                    });
                }
            });
        },
        updateChart(newValue) {
            if (this.ExpandChart) {
                const series = newValue.slice(1).map((row) => ({
                    name: row[0],  // 系列名称，使用每行的第一个元素作为名称
                    type: "line",
                    data: row.slice(1),  // 数据，使用每行的剩余元素作为数据
                    smooth: true,
                    seriesLayoutBy: "row",
                    emphasis: { focus: "series" },
                }));

                series.push({
                    type: "pie",
                    id: "pie",
                    radius: ["15%", "30%"],
                    center: ["50%", "25%"],
                    emphasis: { focus: "self" },
                    label: { formatter: "{b}: {@currentMonth} ({d}%)" },
                    encode: { itemName: "product", value: "currentMonth", tooltip: "currentMonth" },
                    itemStyle: {
                        borderWidth: 5,  // Add a border width to create the gap
                        borderColor: "#fff"  // White border to create a clear separation between slices
                    }
                });

                this.ExpandChart.setOption({
                    dataset: {
                        source: newValue,
                    },
                    xAxis: {
                        data: newValue[0].slice(1),
                    },
                    series,
                });
            }

        },
    },
};
</script>

<style scoped></style>
