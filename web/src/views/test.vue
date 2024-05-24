<!-- <template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld
  }
}
</script>
 -->


 <template>
  <div>
    <textarea ref="editor" v-model="code"></textarea>
  </div>
</template>

<script>
// 引入样式
import 'codemirror/lib/codemirror.css'
// 引入主题色，可更具需求更改或引入更多
import 'codemirror/theme/3024-night.css';

// 引入js脚本
import 'codemirror/mode/javascript/javascript.js';
// 引入编辑器模式为python
import 'codemirror/mode/python/python.js';

import codemirror from "codemirror"

export default {
  data() {
    return {
      // 默认展示的python字符串文本
      code: `import random

# 生成一个随机整数
random_number = random.randint(1, 100)
print("随机数是:", random_number)

# 判断随机数是奇数还是偶数
if random_number % 2 == 0:
    print("这是一个偶数")
else:
    print("这是一个奇数")
`,
      // 设置Codemirror的选项
      options: {
        tabSize: 4, // 设置缩进
        mode: "python", // 设置编辑器语言模式
        lineNumbers: true, // 是否显示行号
        //是否只读
        // readOnly : true,
        lineWrapping: true, // 换行光标位置
        theme: "3024-night" // 主题色
        // 更多选项可以在Codemirror文档中找到：https://codemirror.net/doc/manual.html#config
      }
    }
  },
  methods: {
    clickMe() {
      // 获取实例
      console.log(this.editor);
      // 设置值
      this.editor.setValue("想要赋值的文本");
      // 获取值
      console.log(this.editor.getValue());
    },
    // 初始换，创建codemirror实例
    initeditor() {
      this.editor = codemirror.fromTextArea(this.$refs.editor, this.options);
    }
  },
  mounted() {
    this.initeditor();
  }
}
</script>

