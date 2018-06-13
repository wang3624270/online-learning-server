var saveSelectColor = {
    'Name': 'SelcetColor',
    'Color': 'theme-black'
}

$('body').attr('class', 'theme-white')

// 本地缓存
function storageSave(objectData) {
    localStorage.setItem(objectData.Name, JSON.stringify(objectData));
}

function storageLoad(objectName) {
    if (localStorage.getItem(objectName)) {
        return JSON.parse(localStorage.getItem(objectName))
    } else {
        return false
    }
}