import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import store from 'store/store'
import 'vuetify/dist/vuetify.min.css'

Vue.use(VueResource);
Vue.use(Vuetify);

new Vue({
    el: "#app",
    store,
    vuetify: new Vuetify(),
    render: a => a(App)
});
