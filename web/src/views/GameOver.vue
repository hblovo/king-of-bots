<template>
    <div class="game-over">
      <h1>{{ title }}</h1>
      <p>{{ message }}</p>
      <button @click="playAgain">{{ buttonText }}</button>
    </div>
  </template>
  
  <script>
  export default {
    name: 'GameOver',
    data() {
      return {
        title: 'Game Over',
        message: 'You have lost the game.',
        buttonText: 'Play Again'
      }
    },
    methods: {
      playAgain() {
        // Add your game reset logic here
        this.$router.push({ name: 'home' })
      }
    }
  }
  </script>
  
  <style scoped>
  .game-over {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    background-color: #f2f2f2;
    font-family: Arial, sans-serif;
  }
  
  h1 {
    color: #4CAF50;
    font-size: 48px;
    margin-bottom: 20px;
  }
  
  p {
    font-size: 24px;
    margin-bottom: 30px;
  }
  
  button {
    background-color: #4CAF50;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    border-radius: 4px;
    cursor: pointer;
  }
  </style>