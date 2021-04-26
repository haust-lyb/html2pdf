Vue.component('demo-componet', {
    // language=HTML
    template: `
        <div>
            <h1>hello i'm a componet</h1>
        </div>
    `
})


Vue.component('lyb-data-table', {
    // language=HTML
    template: `
        <div>
            <el-table
                    :data="tb_data"
                    style="width: 100%">
                <el-table-column
                        v-for="item in tb_column"
                        :prop="item.prop"
                        :label="item.label"
                        :width="item.width?item.width:''">
                    <template v-if="!item.is_slot" slot-scope="scope">
                       <slot v-slot:data="scope.row"></slot>
                    </template>
                </el-table-column>
                
            </el-table>
        </div>
    `,
    props:[
        "tb_data",
        "tb_column"
    ]
})
