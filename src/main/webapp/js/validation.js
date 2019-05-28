function validateScript(value) {
    return !String(value).match("<script>.*</script>");
}

function validateMark(mark) {
    return mark != null && mark > 0 && mark <= 100 && /^[1-9]\d*$/.test(mark);
}
