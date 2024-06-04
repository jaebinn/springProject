document.addEventListener('DOMContentLoaded', function() {
    // 버튼 클릭 시 설명 토글
    const buttons = document.querySelectorAll('a[role="button"]');

    buttons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            const id = this.id.replace('button', 'desc');
            const desc = document.getElementById(id);
            if (desc.style.display === "none" || desc.style.display === "") {
                desc.style.display = "block";
            } else {
                desc.style.display = "none";
            }
        });
    });
});