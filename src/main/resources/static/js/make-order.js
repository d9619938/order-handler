const makeOrderButton = document.getElementsByName("addOrderToBucket")[0];
// [0] - означает первый элемент массива (Если мы знаем, что кнопка одна, то лучше указать на это - [0])

let arrArt = localStorage.getItem("products_article");
 console.log(arrArt);
if (!arrArt || arrArt.length < 3) makeOrderButton.style.display = "none";
else makeOrderButton.style.display = "block";

makeOrderButton.addEventListener("click", ()=> {
    //извлекаем список JSON из массива
    arrArt =JSON.parse('[' + arrArt + ']').join(","); //[3, 6, 8, 10]   order/3,6,8,10
    console.log(arrArt);
    fetch("/products/bucket/" + arrArt)
        .then(response =>response.json())
        .then(data => {
            console.log(data);
            makeOrderButton.style.display = "none";
            localStorage.setItem("products_article", JSON.stringify([]));
            document.location='/bucket';
        });
});