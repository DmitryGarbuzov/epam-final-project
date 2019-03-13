

window.history.pushState({}, "");
window.onpopstate = function() {
    history.go(0);
};