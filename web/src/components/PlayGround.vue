<template>
    <div class="playground">
       <GameMap></GameMap>
    </div>
    <div class="button-refresh">
      <button type="button" class="btn btn-primary btn-lg" id="refresh-button">刷新</button>
    </div>
  
  </template>
  
<script>
import GameMap from "../components/GameMap.vue";

export default {
  components: {
    GameMap
  },
  mounted() {
    if (this.$route.path === "/pk/") {
      this.setupRefreshButton();
    }
  },
  watch: {
    '$route.path': function(newPath) {
      if (newPath === '/pk/') {
        this.setupRefreshButton();
      } else {
        this.teardownRefreshButton();
      }
    }
  },
  beforeUnmount() {
    this.teardownRefreshButton();
  },
  methods: {
    setupRefreshButton() {
      this.refreshButton = document.getElementById("refresh-button");
      if (this.refreshButton) {
        this.refreshButtonClickHandler = this.handleRefreshButtonClick.bind(this);
        this.refreshButton.addEventListener("click", this.refreshButtonClickHandler);
      }
    },
    teardownRefreshButton() {
      if (this.refreshButton && this.refreshButtonClickHandler) {
        this.refreshButton.removeEventListener("click", this.refreshButtonClickHandler);
      }
    },
    handleRefreshButtonClick() {
      if (this.$route.path === "/pk/") {
        location.reload();
      }
    }
  }
};
</script>
  
  <style scoped>
  div.playground{
    width:50vw;
    height: 70vh;
    /* background:beige; */
    margin: 40px auto;
  }
  div.button-refresh{
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 40px; 
  }
  </style>