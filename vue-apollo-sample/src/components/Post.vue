<template>
  <div>
    <el-alert
      v-if="showAlert"
      :title="title"
      :description="desc"
      center
      :type="type"
      close-text="知道了"
      @close="close"
    ></el-alert>
    <el-button type="primary" @click="dialogFormVisible = true">新增</el-button>
    <el-dialog title="新增新闻" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.body"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addPost">确 定</el-button>
      </div>
    </el-dialog>
    <avue-crud
      :option="option"
      :data="data"
      :page="page"
      :table-loading="loading"
      @refresh-change="refreshChange"
      @current-change="currentChange"
      @size-change="sizeChange"
      @row-save="rowSave"
    ></avue-crud>
  </div>
</template>

<script>
// import gql from "graphql-tag";
import ADD_POST from "../graphql/AddPost.gql";
import LIST_POST from "../graphql/ListPost.gql";

export default {
  data() {
    return {
      showAlert: false,
      desc: "",
      dialogFormVisible: false,
      type: "success",
      title: "文章创建成功",
      form: {},
      obj: {},
      page: {
        pageSizes: [3, 6, 9],
        currentPage: 1,
        total: 0,
        pageSize: 3
      },
      data: [],
      option: {
        loading: true,
        index: true,
        addBtn: true,
        editBtn: false,
        delBtn: false,
        align: "center",
        menu: false,
        menuAlign: "center",
        column: [
          {
            label: "标题",
            prop: "title"
          },
          {
            label: "内容",
            prop: "body"
          },
          {
            label: "创建时间",
            prop: "createdAt",
            display: false
          }
        ]
      }
    };
  },
  apollo: {
    posts: {
      query: LIST_POST,
      // 响应式参数
      variables() {
        // 在这里使用 vue 响应式属性
        return {
          request: {
             page: this.page.currentPage,
            limit: this.page.pageSize
          }
        };
      },
      error(error) {
        this.$message.error("出错啦" + error);
      },
      watchLoading(isLoading, countModifier) {
        this.loading = isLoading && countModifier === 1;
      },
      result(result) {
        console.log(result.data.listPosts);
        if (result.data.listPosts) {
          this.page.total = result.data.listPosts.count || 0;
          this.data = result.data.listPosts.nodes || [];
        }
      },
      update({ posts }) {
        return posts;
      }
    }
  },
  methods: {
    close() {
      this.showAlert = false;
    },
    rowSave(row, loading, done) {
      this.addPost(row, loading, done);
    },
    refreshChange(param = {}) {
      // 重新拉取一次，可以
      this.$apollo.queries.posts.refetch();
    },
    currentChange(currentPage) {
      this.page.currentPage = currentPage;
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    addPost(row, loading, done) {
      let param = this.dialogFormVisible ? this.form : row;
      if ('createdAt' in param) {
        delete param['createdAt']
      }
      console.log("参数" + JSON.stringify(param));
      // 调用 graphql 变更
      this.$apollo
        .mutate({
          // 查询语句
          mutation: ADD_POST,
          // 参数
          variables: {
            request: {
              ...param
            }
          },
          // 用结果更新缓存
          // 查询将先通过乐观响应、然后再通过真正的变更结果更新
          update: (store, { data: { addPost } }) => {
            let pageParam = {
              request: {
                page: this.page.currentPage,
                limit: this.page.pageSize
              }
            }
            // 从缓存中读取这个查询的数据
            const data = store.readQuery({
              query: LIST_POST,
              variables: pageParam
            });
            console.log(data);
            data.listPosts.count = data.listPosts.count + 1 || 1;
            this.page.total = data.listPosts.count;
            data.listPosts.nodes = data.listPosts.nodes || [];
            // 当前页最后一个删除，添加到第一个元素
            if (data.listPosts.nodes.length > 0) {
              data.listPosts.nodes.pop();
            }
            data.listPosts.nodes.unshift(addPost);
            // 将数据写回缓存
            store.writeQuery({ query: LIST_POST, variables: pageParam, data });
          },
          // 乐观 UI
          // 将在请求产生时作为“假”结果，使用户界面能够快速更新
          optimisticResponse: {
            __typename: "Mutation",
            addPost: {
              __typename: "addPostOutput",
              message: "post.created",
              result: {
                __typename: "Post",
                id: -1,
                title: "",
                body: "",
                createdAt: null
              }
            }
          }
        })
        .then(data => {
          if (loading) {
            loading();
          }
          this.desc = JSON.stringify(data.data.addPost.result);
          this.dialogFormVisible = false;
          this.showAlert = true;
          this.type = "success";
          this.title = "文章创建成功";
          this.form = {};
        })
        .catch(error => {
          if (done) {
            done();
          }
          // 错误
          this.showAlert = true;
          this.dialogFormVisible = false;
          this.type = "error";
          this.title = "创建失败";
          console.log(error);
          this.form = {};
        });
    }
  }
};
</script>
