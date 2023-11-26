
<template>
  <div>
    <br>
    <div id="calculator">
      <marquee dir = "up" class = "id"> es-amin.mohamedamin2026@alexu.edu.eg</marquee>
      <div class="display">{{ result }}</div>
      <div class="buttons">
        <button @click="GetPercent('%')">%</button>
        <button class="button1" @click="clearEntry">CE</button>
        <button class="button1" @click="clearAll">C</button>
        <button class="button1" @click="deleteLastCharacter">DEL</button>
        <button @click="appendToExpression('^(2)')">x<sup> 2</sup></button>
        <button @click="appendToExpression('^(0.5)')"><span style="font-size:19px;">&#8730;<span style="text-decoration:overline">&nbsp;x&nbsp;</span></span></button>
        <button @click="appendToExpression('^(-1)')">x<sup> -1</sup></button>
        <button @click="appendToExpression('/')">÷</button>
        <button @click="appendToExpression('(')">&#40</button>
        <button @click="appendToExpression(')')">&#41</button>
        <button @click="appendToExpression('π')">π</button>
        <button @click="appendToExpression('e')">e</button>        
        <button @click="appendToExpression('1')">1</button>
        <button @click="appendToExpression('2')">2</button>
        <button @click="appendToExpression('3')">3</button>
        <button @click="appendToExpression('+')">+</button>
        <button @click="appendToExpression('4')">4</button>
        <button @click="appendToExpression('5')">5</button>
        <button @click="appendToExpression('6')">6</button>
        <button @click="appendToExpression('-')">-</button>
        <button @click="appendToExpression('7')">7</button>
        <button @click="appendToExpression('8')">8</button>
        <button @click="appendToExpression('9')">9</button>
        <button @click="appendToExpression('*')">x</button>
        <button @click="appendToExpression('0')">0</button>
        <button @click="appendToExpression('.')">.</button>
        <button @click="toggleSign()">+/-</button>
        <button class="button2" @click="evaluateExp()">=</button>
      </div>
    </div>
    <br>
    <div class="hist">
      <h2>Calculation History</h2>
      <ul>
        <li v-for="(calculation, index) in calculationHistory" :key="index">
          {{ calculation }}
        </li>
      </ul>
    </div>
  </div>
</template>

 
<script>

export default { 
  name: "Calculator",
  data() {
      return {
        expression: "",
        result: 0.0,
        isInfinity : false,
        calculationHistory: [],
      };
  },
  methods: {
    GetPercent() {
      const numericValue = parseFloat(this.expression);
      if (!isNaN(numericValue)) {
        this.result = numericValue / 100.0;
        this.expression = this.result.toString();
      } else {
        console.error("Invalid expression for percentage calculation");
        this.expression = "error";
        this.result = "error";
      }
    },
    appendToExpression(value) {
      if(value === 'π') value = Math.PI;
      if(value === 'e') value = Math.E;
      
      this.expression += value;
      this.result = this.expression;
    },
    clearEntry() {
      this.expression = this.expression.slice(0, -1);
      this.result = this.expression;
    },
    clearAll() {
      this.expression = "";
      this.result = 0;
    },
    deleteLastCharacter() {
      this.expression = this.expression.slice(0, -1);
      this.result = this.expression;
    },
    toggleSign() {
        const lastNumberIndex = this.findLastNumberIndex();
        
        if (lastNumberIndex !== -1) {
          const numericValue = parseFloat(this.expression.slice(lastNumberIndex));
          
          if (!isNaN(numericValue)) {
            const toggledValue = numericValue < 0 ? Math.abs(numericValue) : -numericValue;
            this.expression = this.expression.slice(0, lastNumberIndex) + toggledValue;
            this.result = this.expression;
          } else {
            console.error("Invalid expression for sign toggling");
            this.expression = "Error";
            this.result = this.expression;
          }
        }
      },
    findLastNumberIndex() {
      const operators = ['+', '-', '*', '/', '^'];
      let lastNumberIndex = -1;

      for (let i = this.expression.length - 1; i >= 0; i--) {
        if (operators.includes(this.expression[i])) {
          break;
        } else if (this.isNumeric(this.expression[i])) {
          lastNumberIndex = i;
        }
      }

      return lastNumberIndex;
    },
    isNumeric(value) {
      return !isNaN(parseFloat(value)) && isFinite(value);
    },
    calculateResult() {
      try {
        const calculatedResult = eval(this.expression);
        this.result = calculatedResult;
      }catch (error) {
        this.result = 'Error';
      }
    },
    evaluateExp(){
      fetch("http://localhost:8080/eval",{
          method:"POST",
          headers : {
            'Content-type':'application/json',
          },
          body : JSON.stringify(this.expression)
        }).then(responce=>{
          return responce.text();
        }).then(data =>{
          console.log(data);
          this.result = data;
          if(this.result === "Infinity") this.result = "Cannot divide by zero";
          if(this.result === "NaN") this.result = "No square root for negative value";
          this.calculationHistory.push(`${this.expression} = ${this.result}`);
          this.expression = this.result;
        }).catch(error => {
          console.error("Error:", error);
          this.result = "Error";
          this.expression = "Error";
          this.isError = true;
        });
    }
  } 
};
</script>

<style scoped>

  #calculator {
    width: 300px;
    margin:auto;
    text-align: right;
    padding: 20px;
    background-color: hsla(0, 0%, 100%, 0.646);
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(255, 255, 255, 0);
  }

  .display {
    margin-bottom: 10px;
    font-size: 24px;
    padding: 10px;
    background-color: hsla(0, 4%, 5%, 0.646);
    border: 5px solid rgba(255, 255, 255, 0.265);
    border-radius: 5px;
    color: rgb(255, 255, 255);
  }
  .id{
    color:rgb(0, 0, 0);
  }
  .buttons {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-gap: 10px;
  }
  button {
    font-size: 18px;
    padding: 10px;
    background-color: rgb(255, 255, 255);
    color: rgb(0, 0, 0);
    border: none;
    border-radius: 15%;
    cursor: pointer;
    transition-duration: 0.3s;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0);
  }
  .button1:hover {background-color: rgb(254, 0, 0)}
  .button2:hover {background-color: rgb(21, 159, 67)}

  .hist{
    color: rgb(0, 0, 0);
    text-align: center;
    border: 5px solid rgb(0, 0, 0);
    background-color: #ffffff;
    margin: auto;
    padding: 1%;
  }
</style>
