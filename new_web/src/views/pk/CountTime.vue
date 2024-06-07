<template>
    <div v-if="showModal" class="modal-overlay">
      <div class="modal-content">
        <p class="modal-title">匹配成功！</p>
        <p class="modal-countdown">即将进入对战页面... {{ countdown }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        showModal: true,
        countdown: 3,
      };
    },
    mounted() {
      this.startCountdown();
    },
    methods: {
      startCountdown() {
        let countdownInterval = setInterval(() => {
          if (this.countdown > 0) {
            this.countdown--;
          } else {
            clearInterval(countdownInterval);
            this.closeModal();
          }
        }, 1000);
      },
      closeModal() {
        this.showModal = false;
        this.$emit('countdown-finished');
      }
    }
  };
  </script>
  
  <style>
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    border-radius: 10px;
  }
  .modal-content {
    background: white;
    padding: 40px;
    border-radius: 10px;
    text-align: center;
    width: 400px;
    max-width: 90%;
  }
  .modal-title {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 20px;
  }
  .modal-countdown {
    font-size: 20px;
  }
  </style>
  