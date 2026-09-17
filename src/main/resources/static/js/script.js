/**
 * Small shared UI helpers for the Self Scheduling System.
 */
document.addEventListener('DOMContentLoaded', function () {
    // Auto-hide success/error alerts after a few seconds
    var alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alertBox) {
        setTimeout(function () {
            alertBox.style.transition = 'opacity 0.5s ease';
            alertBox.style.opacity = '0';
            setTimeout(function () {
                alertBox.remove();
            }, 500);
        }, 4000);
    });
});
