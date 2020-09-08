package com.zizohanto.android.tobuy.data.mappers

import com.zizohanto.android.tobuy.data.mappers.base.EntityMapper
import com.zizohanto.android.tobuy.data.models.ProductEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsEntity
import com.zizohanto.android.tobuy.domain.models.Product
import com.zizohanto.android.tobuy.domain.models.ShoppingList
import com.zizohanto.android.tobuy.domain.models.ShoppingListWithProducts
import javax.inject.Inject

class ShoppingListWithProductsEntityMapper @Inject constructor(
    private val shoppingListMapper: ShoppingListEntityMapper,
    private val productsMapper: ProductEntityMapper,
) : EntityMapper<ShoppingListWithProductsEntity, ShoppingListWithProducts> {

    override fun mapFromEntity(entity: ShoppingListWithProductsEntity): ShoppingListWithProducts {
        val shoppingList: ShoppingList = shoppingListMapper.mapFromEntity(entity.shoppingListEntity)
        val products: List<Product> = productsMapper.mapFromEntityList(entity.productEntities)
        return ShoppingListWithProducts(shoppingList, products)
    }

    override fun mapToEntity(domain: ShoppingListWithProducts): ShoppingListWithProductsEntity {
        val shoppingList: ShoppingListEntity = shoppingListMapper.mapToEntity(domain.shoppingList)
        val products: List<ProductEntity> = productsMapper.mapFromDomainList(domain.products)
        return ShoppingListWithProductsEntity(shoppingList, products)
    }
}