import Vue from 'vue'
import VueRouter from 'vue-router'

// 필수
Vue.use(VueRouter)

const routes = [
    {
        path: "/",
        name: "HelloWorld",
        component: () => import('./../components/HelloWorld.vue')
    },
    {
        path: "/signup",
        name: "SignUp",
        component: () => import('../components/Users/SignUp.vue')
    },
    {
        path: "/signin",
        name: "Signin",
        component: () => import('../components/Users/SignIn.vue')
    },
    {
        path: '/mypage',
        name: "MyPage",
        component: () => import('../components/Users/MyPage.vue')
    },
    {
        path: "/posts/",
        name: "PostsView",
        component: () => import('./../components/Posts/PostsView.vue')
    },
    {
        path: "/posts/insert/",
        name: "PostsInsert",
        component: () => import('./../components/Posts/PostsInsert.vue')
    }
]
const router = new VueRouter({
    mode: 'history',
    routes
})

export default router