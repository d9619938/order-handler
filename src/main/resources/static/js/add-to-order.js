const addToOrderButton = document.getElementsByName("Добавить в заказ");
const removeFromOrderButton = document.getElementsByName("Удалить из заказа");

let articles = localStorage.getItem("products_article");
if (!articles || articles.length < 1) articles = [];
else articles = JSON.parse(articles);

// addToOrderButton.forEach(button => {
//     const productArticle = button.dataset.productArticle;
//     if (articles.length === 0 || articles.indexOf(productArticle) < 0) {
//         button.style.display = "block";
//         button.nextElementSibling.style.display = "none";
//         return;
//     }
//     button.style.display = "none";
//     button.nextElementSibling.style.display = "block";
// });


addToOrderButton.forEach(button => {
    button.addEventListener("click", ()=>{
        const productArticle = button.dataset.productArticle;
        if (articles.indexOf(productArticle) < 0) {
            articles.push(productArticle);
            localStorage.setItem("products_article", JSON.stringify(articles));
        }
        button.style.display = "none";
        button.nextElementSibling.style.display = "block"
        button.nextElementSibling.style.color = "red";

    });
});

removeFromOrderButton.forEach(button => {
    button.addEventListener("click", ()=>{
        const pictureArticle = button.dataset.pictureArticle;
        if (articles.indexOf(pictureArticle) >= 0) {
            articles.splice(articles.indexOf(pictureArticle), 1);
            localStorage.setItem("products_article", JSON.stringify(articles));
        }
        button.style.display = "none";
        button.previousElementSibling.style.display = "block";

    });
});
