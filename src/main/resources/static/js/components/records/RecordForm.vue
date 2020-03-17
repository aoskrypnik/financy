<template>
    <v-layout column>
        <v-text-field
                label="New sum"
                placeholder="Write something"
                v-model="sum"
        />
        <v-text-field
                label="New category"
                placeholder="Write something"
                v-model="category"
        />
        <v-text-field
                label="New comment"
                placeholder="Write something"
                v-model="comment"
        />
        <v-btn @click="save">Save</v-btn>
    </v-layout>
</template>

<script>
    export default {
        props: ['records'],
        data() {
            return {
                sum: '',
                category: '',
                comment: ''
            }
        },
        methods: {
            save() {
                const record = {sum: this.sum, category: this.category, comment: this.comment};

                this.$resource('/income{/id}').save({}, record).then(result =>
                    result.json().then(data => {
                        this.records.push(data);
                        this.sum = '';
                        this.category = '';
                        this.comment = '';
                    }))
            }
        }
    }
</script>

<style>

</style>