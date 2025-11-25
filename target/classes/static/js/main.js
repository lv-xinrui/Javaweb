// 文华学院网站主要JavaScript功能

document.addEventListener('DOMContentLoaded', function () {
    // 初始化工具提示
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // 初始化弹出框
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });

    // 平滑滚动
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });

    // 返回顶部按钮
    createBackToTopButton();

    // 图片懒加载
    initLazyLoading();

    // 搜索功能
    initSearch();

    // 新闻分享功能
    initShareButtons();
});

// 创建返回顶部按钮
function createBackToTopButton() {
    const backToTopButton = document.createElement('button');
    backToTopButton.innerHTML = '<i class="fas fa-arrow-up"></i>';
    backToTopButton.className = 'btn btn-primary position-fixed';
    backToTopButton.style.cssText = `
        bottom: 20px;
        right: 20px;
        z-index: 1000;
        border-radius: 50%;
        width: 50px;
        height: 50px;
        display: none;
        box-shadow: 0 4px 8px rgba(0,0,0,0.2);
    `;
    backToTopButton.setAttribute('aria-label', '返回顶部');

    document.body.appendChild(backToTopButton);

    // 显示/隐藏按钮
    window.addEventListener('scroll', function () {
        if (window.pageYOffset > 300) {
            backToTopButton.style.display = 'block';
        } else {
            backToTopButton.style.display = 'none';
        }
    });

    // 点击返回顶部
    backToTopButton.addEventListener('click', function () {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });
}

// 初始化图片懒加载
function initLazyLoading() {
    if ('IntersectionObserver' in window) {
        const imageObserver = new IntersectionObserver((entries, observer) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const img = entry.target;
                    img.src = img.dataset.src;
                    img.classList.remove('lazy');
                    imageObserver.unobserve(img);
                }
            });
        });

        document.querySelectorAll('img[data-src]').forEach(img => {
            imageObserver.observe(img);
        });
    }
}

// 初始化搜索功能
function initSearch() {
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        let searchTimeout;

        searchInput.addEventListener('input', function () {
            clearTimeout(searchTimeout);
            searchTimeout = setTimeout(() => {
                performSearch(this.value);
            }, 300);
        });
    }
}

// 执行搜索
function performSearch(query) {
    if (query.length < 2) return;

    // 这里可以实现AJAX搜索功能
    console.log('搜索:', query);

    // 示例：跳转到搜索结果页面
    // window.location.href = '/search?q=' + encodeURIComponent(query);
}

// 初始化分享按钮
function initShareButtons() {
    document.querySelectorAll('[data-share]').forEach(button => {
        button.addEventListener('click', function (e) {
            e.preventDefault();
            const shareData = {
                title: this.dataset.title || document.title,
                url: this.dataset.url || window.location.href,
                text: this.dataset.text || ''
            };

            if (navigator.share) {
                navigator.share(shareData).catch(console.error);
            } else {
                // 降级处理：复制链接到剪贴板
                navigator.clipboard.writeText(shareData.url).then(() => {
                    showToast('链接已复制到剪贴板', 'success');
                }).catch(() => {
                    showToast('复制失败，请手动复制链接', 'error');
                });
            }
        });
    });
}

// 显示提示消息
function showToast(message, type = 'info') {
    const toast = document.createElement('div');
    toast.className = `toast align-items-center text-white bg-${type === 'success' ? 'success' : type === 'error' ? 'danger' : 'primary'} border-0`;
    toast.setAttribute('role', 'alert');
    toast.setAttribute('aria-live', 'assertive');
    toast.setAttribute('aria-atomic', 'true');

    toast.innerHTML = `
        <div class="d-flex">
            <div class="toast-body">
                ${message}
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
    `;

    // 添加到页面
    let toastContainer = document.querySelector('.toast-container');
    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.className = 'toast-container position-fixed top-0 end-0 p-3';
        toastContainer.style.zIndex = '9999';
        document.body.appendChild(toastContainer);
    }

    toastContainer.appendChild(toast);

    // 显示toast
    const bsToast = new bootstrap.Toast(toast);
    bsToast.show();

    // 自动移除
    toast.addEventListener('hidden.bs.toast', () => {
        toast.remove();
    });
}

// 格式化日期
function formatDate(dateString) {
    const date = new Date(dateString);
    const now = new Date();
    const diff = now - date;

    if (diff < 60000) { // 1分钟内
        return '刚刚';
    } else if (diff < 3600000) { // 1小时内
        return Math.floor(diff / 60000) + '分钟前';
    } else if (diff < 86400000) { // 24小时内
        return Math.floor(diff / 3600000) + '小时前';
    } else if (diff < 604800000) { // 7天内
        return Math.floor(diff / 86400000) + '天前';
    } else {
        return date.toLocaleDateString('zh-CN');
    }
}

// 防抖函数
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// 节流函数
function throttle(func, limit) {
    let inThrottle;
    return function () {
        const args = arguments;
        const context = this;
        if (!inThrottle) {
            func.apply(context, args);
            inThrottle = true;
            setTimeout(() => inThrottle = false, limit);
        }
    };
}

// 加载更多内容
function loadMore(url, container, callback) {
    const loadingElement = document.createElement('div');
    loadingElement.className = 'text-center py-3';
    loadingElement.innerHTML = '<div class="loading"></div> 加载中...';

    container.appendChild(loadingElement);

    fetch(url)
        .then(response => response.json())
        .then(data => {
            loadingElement.remove();
            if (callback) {
                callback(data);
            }
        })
        .catch(error => {
            loadingElement.innerHTML = '<div class="text-danger">加载失败，请重试</div>';
            console.error('加载失败:', error);
        });
}

// 复制到剪贴板
function copyToClipboard(text) {
    if (navigator.clipboard) {
        return navigator.clipboard.writeText(text);
    } else {
        // 降级处理
        const textArea = document.createElement('textarea');
        textArea.value = text;
        document.body.appendChild(textArea);
        textArea.select();
        try {
            document.execCommand('copy');
            return Promise.resolve();
        } catch (err) {
            return Promise.reject(err);
        } finally {
            document.body.removeChild(textArea);
        }
    }
}

// 验证表单
function validateForm(form) {
    const inputs = form.querySelectorAll('input[required], textarea[required], select[required]');
    let isValid = true;

    inputs.forEach(input => {
        if (!input.value.trim()) {
            input.classList.add('is-invalid');
            isValid = false;
        } else {
            input.classList.remove('is-invalid');
        }
    });

    return isValid;
}

// 导出全局函数
window.WenHuaUtils = {
    showToast,
    formatDate,
    debounce,
    throttle,
    loadMore,
    copyToClipboard,
    validateForm
};
