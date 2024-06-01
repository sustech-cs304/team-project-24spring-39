import {
  fetchAllPostsInside,
  fetchMyPostsInside,
  fetchMajorCoursesInside,
  applyFilterInside,
  // applyFilterInside,
  // createPostInside,
  deletePostInside,
  makeCommentInside,
  getPageNum,
} from "@/api/forum_api";
import { ElMessageBox } from "element-plus";
export default {
  state: {
    post_num: 25,
    major_courses: [
      {
        name: "Computer Science",
        courses: ["Algorithms", "Data Structures", "Operating Systems"],
      },
      {
        name: "Electrical Engineering",
        courses: ["Circuit Analysis", "Electromagnetics", "Signal Processing"],
      },
    ],
    filter_info: {
      major: -1,
      course: -1,
      page_start: 0,
      page_end: 7,
    },
    all_posts: [
      {
        id: 1,
        title: "帖子标题1",
        content: "这是第一个帖子内容。",
        name: "用户1",
        date: "2024-05-22",
        attachments: [
          { name: "Attachment 1", url: "path/to/attachment1.pdf" },
          { name: "Attachment 2", url: "path/to/attachment2.docx" },
        ],
        comments: [
          { id: 1, name: "User1", content: "This is the first comment." },
          { id: 2, user: "User2", content: "This is the second comment." },
          { id: 3, user: "User3", content: "This is the third comment." },
        ],
      },
      {
        id: 2,
        title: "帖子标题2",
        content: "这是第二个帖子内容。",
        name: "用户2",
        date: "2024-05-21",
        attachments: [
          { name: "Attachment 1", url: "path/to/attachment1.pdf" },
          { name: "Attachment 2", url: "path/to/attachment2.docx" },
        ],
        comments: [
          { id: 1, user: "User1", content: "This is the first comment." },
          { id: 2, user: "User2", content: "This is the second comment." },
          { id: 3, user: "User3", content: "This is the third comment." },
        ],
      },
      {
        id: 3,
        title: "帖子标题2",
        content: "这是第二个帖子内容。",
        name: "用户2",
        date: "2024-05-21",
        attachments: [
          { name: "Attachment 1", url: "path/to/attachment1.pdf" },
          { name: "Attachment 2", url: "path/to/attachment2.docx" },
        ],
        comments: [
          { id: 1, user: "User1", content: "This is the first comment." },
          { id: 2, user: "User2", content: "This is the second comment." },
          { id: 3, user: "User3", content: "This is the third comment." },
        ],
      },
      {
        id: 4,
        title: "帖子标题2",
        content: "这是第二个帖子内容。",
        name: "用户2",
        date: "2024-05-21",
        attachments: [
          { name: "Attachment 1", url: "path/to/attachment1.pdf" },
          { name: "Attachment 2", url: "path/to/attachment2.docx" },
        ],
        comments: [
          { id: 1, user: "User1", content: "This is the first comment." },
          { id: 2, user: "User2", content: "This is the second comment." },
          { id: 3, user: "User3", content: "This is the third comment." },
        ],
      },
    ],
    my_posts: [
      {
        id: 1,
        title: "Post Title 1",
        content:
          "This is a brief descripti This is a brief description of post 2",
        name: "用户2",
        date: "2024-05-21",
        attachments: [
          { name: "Attachment 1", url: "path/to/attachment1.pdf" },
          { name: "Attachment 2", url: "path/to/attachment2.docx" },
        ],
        comments: [
          { id: 1, user: "User1", content: "This is the first comment." },
          { id: 2, user: "User2", content: "This is the second comment." },
          { id: 3, user: "User3", content: "This is the third comment." },
        ],
      },
      {
        id: 2,
        title: "Post Title 2This is a brief description of post 2",
        content: "This is a brief description of post 2",
        name: "用户2",
        date: "2024-05-21",
        attachments: [
          { name: "Attachment 1", url: "path/to/attachment1.pdf" },
          { name: "Attachment 2", url: "path/to/attachment2.docx" },
        ],
        comments: [
          { id: 1, user: "User1", content: "This is the first comment." },
          { id: 2, user: "User2", content: "This is the second comment." },
          { id: 3, user: "User3", content: "This is the third comment." },
        ],
      },
      {
        id: 3,
        title: "Post Title 3",
        content: "This is a brief description of post 3",
        name: "用户2",
        date: "2024-05-21",
        attachments: [
          { name: "Attachment 1", url: "path/to/attachment1.pdf" },
          { name: "Attachment 2", url: "path/to/attachment2.docx" },
        ],
        comments: [
          { id: 1, user: "User1", content: "This is the first comment." },
          { id: 2, user: "User2", content: "This is the second comment." },
          { id: 3, user: "User3", content: "This is the third comment." },
        ],
      },
    ],

    selectedPost: {},
  },

  mutations: {
    set_major_courses(state, major_courses) {
      state.major_courses = major_courses;
    },

    set_all_posts(state, all_posts) {
      state.all_posts = all_posts;
    },

    set_my_posts(state, my_posts) {
      state.my_posts = my_posts;
    },

    delete_post(state, postID) {
      state.all_posts = state.all_posts.filter((item) => item.id !== postID);
      state.my_posts = state.my_posts.filter((item) => item.id !== postID);
    },

    make_comment(state, newComment) {
      state.selectedPost.comments.push(newComment);
    },

    setSelectedPost(state, postId) {
      state.selectedPost = state.all_posts.find((p) => p.id == postId);
    },

    setMajorCourses(state, major, course) {
      state.filter_info.major = major;
      state.filter_info.course = course;
    },

    set_start_end(state, start, end) {
      state.filter_info.start = start;
      state.filter_info.end = end;
    },

    set_post_num(state, num) {
      state.post_num = num;
    },
  },

  actions: {
    async fetchForum({ commit }) {
      try {
        console.log("start fetching");
        const response1 = await fetchMajorCoursesInside();
        commit("set_major_courses", response1.data.major_courses);
        const response2 = await fetchAllPostsInside();
        commit("set_all_posts", response2.data.all_posts);
        const response3 = await fetchMyPostsInside();
        commit("set_my_posts", response3.data.my_posts);
        const response4 = await getPageNum();
        commit("set_post_num", response4.data);
      } catch (error) {
        console.error("Error:", error);
      }
    },

    async deletePost({ commit }, postId) {
      try {
        await deletePostInside(postId);
        ElMessageBox.alert("删除成功", "成功", {
          confirmButtonText: "确定",
          type: "success",
        });
        commit("delete_post", postId);
      } catch (error) {
        console.error("Error:", error);
        ElMessageBox.alert("删除失败", "错误", {
          confirmButtonText: "确定",
          type: "error",
        });
      }
    },

    async makeComment({ commit }, newComment) {
      try {
        await makeCommentInside(newComment);
        ElMessageBox.alert("评论成功", "成功", {
          confirmButtonText: "确定",
          type: "success",
        });
        commit("make_comment", newComment);
      } catch (error) {
        console.error("Error:", error);
        ElMessageBox.alert("评论失败", "错误", {
          confirmButtonText: "确定",
          type: "error",
        });
      }
    },
  },

  async applyFilter({ commit }, payLoad) {
    try {
      const response = await applyFilterInside(payLoad);
      ElMessageBox.alert("发帖成功", "成功", {
        confirmButtonText: "确定",
        type: "success",
      });
      commit("set_all_posts", response.data);
    } catch (error) {
      console.error("Error:", error);
      ElMessageBox.alert("发帖失败", "错误", {
        confirmButtonText: "确定",
        type: "error",
      });
    }
  },
};
