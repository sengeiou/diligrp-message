/**
 * 通用格式化显示tip
 * @param value
 * @param row
 * @param index
 */
function formatForTip(value,row,index) {
    if (value){
        return "<span title='" + value + "'>" + value + "</span>";
    }
    return "";
}