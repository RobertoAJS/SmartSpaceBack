

// Scroll suave al hacer clic en los enlaces del menú
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener("click", function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute("href"));
        if (target) {
            target.scrollIntoView({ behavior: "smooth", block: "start" });
        }
    });
});

// Efecto "shrink" en el header al hacer scroll
window.addEventListener("scroll", () => {
    const header = document.querySelector("header");
    if (window.scrollY > 80) {
        header.style.padding = "0.6rem 1.5rem";
        header.style.boxShadow = "0 3px 10px rgba(0,0,0,0.15)";
    } else {
        header.style.padding = "1.2rem 2rem";
        header.style.boxShadow = "0 2px 6px rgba(0,0,0,0.1)";
    }
});

// Animaciones on-scroll (fade-in) para secciones
const fadeElements = document.querySelectorAll(
    ".about, .features, .contact, .feature-card"
);

const fadeInOnScroll = () => {
    fadeElements.forEach(el => {
        const rect = el.getBoundingClientRect();
        if (rect.top < window.innerHeight - 100) {
            el.style.opacity = 1;
            el.style.transform = "translateY(0)";
            el.style.transition = "opacity 1s ease, transform 1s ease";
        }
    });
};

// Inicializa animación
fadeElements.forEach(el => {
    el.style.opacity = 0;
    el.style.transform = "translateY(40px)";
});

window.addEventListener("scroll", fadeInOnScroll);
window.addEventListener("load", fadeInOnScroll);

// Confirmación de envío en el formulario de contacto
const contactForm = document.querySelector("form");
if (contactForm) {
    contactForm.addEventListener("submit", e => {
        alert("Gracias por contactarte con Smartspace. ¡Te responderemos pronto!");
    });
}
