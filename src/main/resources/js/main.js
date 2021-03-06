import Vue from 'vue'
import router from 'router/router'
import 'api/resource'
import App from 'pages/App.vue'
import Vuetify from 'vuetify'
import Vuelidate from 'vuelidate'
import '@babel/polyfill'
import store from 'store/store'
import VueGoogleCharts from 'vue-google-charts'
import 'vuetify/dist/vuetify.min.css'

Vue.use(Vuetify, {
    iconfont: 'md'
})
Vue.use(VueGoogleCharts)
Vue.use(Vuelidate)

new Vue({
    el: "#app",
    store,
    router,
    vuetify: new Vuetify(),
    render: a => a(App)
});
