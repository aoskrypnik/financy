import Vue from 'vue'
import VueRouter from 'vue-router'
import RecordList from "../pages/RecordList.vue"
import Auth from "../pages/Auth.vue"
import Chart from "../pages/Chart.vue"

Vue.use(VueRouter)

const routes = [
    {path: '/', component: RecordList},
    {path: '/auth', component: Auth},
    {path: '/chart', component: Chart},
    {path: '*', component: RecordList}
]

export default new VueRouter({
    mode: 'history',
    routes
})