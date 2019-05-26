function validateScript(value) {
    return !String(value).match("<script>.*</script>");
}