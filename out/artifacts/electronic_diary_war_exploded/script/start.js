$(document).ready(function(){
    $(".owl-carousel").owlCarousel({
      items: 1,
      loop: true,
      autoplay: true,
      autoplayTimeout: 4000, 
      smartSpeed: 1000,
      mouseDrag: false,
      touchDrag: false,
      pullDrag: false
    });
});

var modal = document.getElementById('id01');
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
        document.getElementsByClassName("email").item(0).value = "";
    }
};

window.history.pushState({}, "");
window.onpopstate = function() {
    history.go(1);
};
