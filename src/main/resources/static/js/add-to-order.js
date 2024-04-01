const addToOrderButton
    = document.getElementsByName("Добавить в заказ");
const removeFromOrderButton
    = document.getElementsByName("Удалить из заказа");

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
        }
        if (arrArt.indexOf(productArticle) < 0) {
            button.style.display = "none";
            // button.nextElementSibling.style.backgroundColor = "initial";
        }
    });
});