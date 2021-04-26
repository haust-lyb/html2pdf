$.ajaxSetup({
    layerIndex:-1,
    beforeSend: function () {
        this.layerIndex = layer.load();
    },
    complete: function () {
        layer.close(this.layerIndex);
    },
    error: function () {
        layer.close(this.layerIndex);
        layer.alert('请求失败了', {
            icon: 5,
            skin: 'layer-ext-moon'
        })
    }
});
