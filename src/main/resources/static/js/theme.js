/**
 * Applies the gender-based theme to the current page.
 * Only affects colors/visual styling - never functionality or behavior.
 */
function applyTheme(gender) {
    var body = document.getElementById('formBody') || document.body;
    if (gender === 'MALE' || gender === 'FEMALE') {
        body.setAttribute('data-theme', gender);
    } else {
        body.removeAttribute('data-theme');
    }
}
