// const makeOrderButton = document.getElementsByName("addOrderToBucket")[0];
// // [0] - означает первый элемент массива (Если мы знаем, что кнопка одна, то лучше указать на это - [0])
//
// let makeArrArt = localStorage.getItem("products_article");
//  console.log(makeArrArt);
// if (!makeArrArt || makeArrArt.length < 3) makeOrderButton.style.display = "none";
// else makeOrderButton.style.display = "block";
//
// makeOrderButton.addEventListener("click", ()=> {
//     //извлекаем список JSON из массива
//     makeArrArt =JSON.parse('[' + makeArrArt + ']').join(","); //[3, 6, 8, 10]   order/3,6,8,10
//     console.log(makeArrArt);
//     fetch("/products/bucket/" + makeArrArt)
//         .then(response =>response.json())
//         .then(data => {
//             console.log(data);
//             makeOrderButton.style.display = "none";
//             localStorage.setItem("products_article", JSON.stringify([]));
//             document.location='/bucket';
//         });
// });