<template>
    <v-row class="justify-center mb-3">
        <v-btn x-small class="mr-2 transparent"
               :disabled="isCurrentDateFirstInList(curDate.dateString, dateListGetter)"
               @click="moveDateLeft">
            <v-icon>keyboard_arrow_left</v-icon>
        </v-btn>
        <v-data>{{curDate.dateString}}</v-data>
        <v-btn x-small class="ml-2 transparent"
               :disabled="isCurrentDateLastInList(curDate.dateString, dateListGetter)"
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
            }
        },
        methods: {
            ...mapActions([
                'moveDateLeftAction',
                'moveDateRightAction'
            ]),
            isCurrentDateFirstInList(current, list) {
                if (list[0] === undefined) return true;
                return list[0].dateString === current;
            },
            isCurrentDateLastInList(current, list) {
                if (list[list.length - 1] === undefined) return true;
                return list[list.length - 1].dateString === current;
            },
            moveDateLeft() {
                const leftDate = this.dateListGetter[this.dateListGetter.indexOf(this.curDate) - 1];
                this.moveDateLeftAction(leftDate);
            },
            moveDateRight() {
                const rightDate = this.dateListGetter[this.dateListGetter.indexOf(this.curDate) + 1];
                this.moveDateRightAction(rightDate);
            },
        }
    }
</script>

<style>

</style>