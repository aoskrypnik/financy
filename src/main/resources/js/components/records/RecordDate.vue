<template>
    <v-row class="justify-center mb-3">
        <v-btn x-small class="mr-2 transparent"
               :disabled="isCurrentDateFirstInList"
               @click="moveDateLeft">
            <v-icon>keyboard_arrow_left</v-icon>
        </v-btn>
        <v-data>{{curDate.dateString}}</v-data>
        <v-btn x-small class="ml-2 transparent"
               :disabled="isCurrentDateLastInList"
               @click="moveDateRight">
            <v-icon>keyboard_arrow_right</v-icon>
        </v-btn>
    </v-row>
</template>

<script>
    import {mapActions, mapGetters} from 'vuex'

    export default {
        computed: {
            ...mapGetters([
                'dateListGetter'
            ]),
            curDate() {
                let date = new Date();
                const ye = new Intl.DateTimeFormat('en', {year: 'numeric'}).format(date);
                const mo = new Intl.DateTimeFormat('en', {month: 'short'}).format(date);
                let tempRes = {dateString: ye + ' ' + mo};
                let curDate = this.$store.getters.dateListGetter.find(e => e.isCurrent === true);
                return curDate === undefined ? tempRes : curDate
            },
            isCurrentDateFirstInList() {
                if (this.dateListGetter === undefined) return true
                return (this.dateListGetter[0] === undefined)
                    ? true
                    : this.dateListGetter[0].dateString === this.curDate.dateString
            },
            isCurrentDateLastInList() {
                if (this.dateListGetter === undefined) return true
                return (this.dateListGetter[this.dateListGetter.length - 1] === undefined)
                    ? true
                    : this.dateListGetter[this.dateListGetter.length - 1].dateString === this.curDate.dateString;
            },
        },
        methods: {
            ...mapActions([
                'moveDateLeftAction',
                'moveDateRightAction'
            ]),
            moveDateLeft() {
                const leftDate = this.dateListGetter[this.dateListGetter.indexOf(this.curDate) - 1];
                this.moveDateLeftAction(leftDate);
            },
            moveDateRight() {
                const rightDate = this.dateListGetter[this.dateListGetter.indexOf(this.curDate) + 1];
                this.moveDateRightAction(rightDate);
            },
        },
    }
</script>

<style>

</style>