const addToOrderButton
    = document.getElementsByName("Добавить в заказ");
const removeFromOrderButton
    = document.getElementsByName("Удалить из заказа");
const makeOrderButton = document.getElementsByName("addOrderToBucket")[0];
// [0] - означает первый элемент массива (Если мы знаем, что кнопка одна, то лучше указать на это - [0])

let arrArt = localStorage.getItem("products_article");
if (!arrArt || arrArt.length < 3) arrArt = [];
else arrArt = JSON.parse(arrArt);

addToOrderButton.forEach(button => {
    const productArticle = button.dataset.productArticle;
    if (arrArt.length === 0 || arrArt.indexOf(productArticle) < 0) {
        // button.style.display = "block"
        button.nextElementSibling.style.display = "none";
        return;
    }
    // button.style.display = "none";
    button.nextElementSibling.style.display = "block";
});


addToOrderButton.forEach(button =>{
   button.addEventListener("click", ()=>{
      const productArticle = button.dataset.productArticle;
      arrArt.push(productArticle);
      if (makeOrderButton.style.display === "none") {
          makeOrderButton.style.display = "block";
      }
      localStorage.setItem("products_article", JSON.stringify(arrArt));

      if(arrArt.indexOf(productArticle) >= 0) {
          // button.style.backgroundColor = "#59de7a";
          button.nextElementSibling.style.display = "block";
          // button.nextElementSibling.style.backgroundColor = "#fa2a2a";
      }
   });
});

removeFromOrderButton.forEach(button =>{
    button.addEventListener("click", () =>{
        const productArticle = button.dataset.productArticle;
        if (arrArt.indexOf(productArticle) >=0) {
            arrArt.splice(arrArt.indexOf(productArticle), 1);
            localStorage.setItem("products_article", JSON.stringify(arrArt));
            if (makeOrderButton.style.display === "block"){
                makeOrderButton.style.display = "none";
            }
        }
        if (arrArt.indexOf(productArticle) < 0) {
            button.style.display = "none";
            // button.nextElementSibling.style.backgroundColor = "initial";
        }
    });
});



let makeArrArt = localStorage.getItem("products_article");
console.log(makeArrArt);
if (!makeArrArt || makeArrArt.length < 3) makeOrderButton.style.display = "none";
else makeOrderButton.style.display = "block";

makeOrderButton.addEventListener("click", ()=> {
    //извлекаем список JSON из массива
    makeArrArt =JSON.parse('[' + makeArrArt + ']').join(","); //[3, 6, 8, 10]   order/3,6,8,10
    console.log(makeArrArt);
    fetch("/products/bucket/" + makeArrArt)
        .then(response =>response.json())
        .then(data => {
            console.log(data);
            makeOrderButton.style.display = "none";
            localStorage.setItem("products_article", JSON.stringify([]));
            document.location='/bucket';
        });
});