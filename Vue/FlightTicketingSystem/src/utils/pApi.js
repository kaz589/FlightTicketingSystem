// 新增商品
addAirport: (airport) => instance.post('/airports', airport),

    // 刪除商品
    deleteAirport: (id) => instance.delete(`/airports/${id}`),
        // 更新商品
        updateAirport: (id, airport) => instance.put(`/airports/${id}`, airport),