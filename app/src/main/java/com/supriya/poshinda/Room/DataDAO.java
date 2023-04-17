package com.supriya.poshinda.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDAO {

    //Product DAO Methods
    @Query("SELECT * FROM PRODUCTENTITY")
    List<ProductEntity> getAllProduct();

    @Insert
    void insertProduct(ProductEntity productEntity);

    //Soil Test Form DAO Methods
    @Query("SELECT * FROM SoilTestFormEntity")
    List<SoilTestFormEntity> getAllForm();

    @Insert
    void insertFormData(SoilTestFormEntity soilTestFormEntity);

    @Query("SELECT * FROM SoilTestFormEntity WHERE farmerName = :farmerName")
    int isDataExist(String farmerName);

    @Query("UPDATE SoilTestFormEntity SET status = :s WHERE farmerId = :id")
    void updateByFarmerId(String s,int id);

    //Cart Entry DAO Methods
    @Insert
    void insertrecord(CartEntity product);

    @Query("SELECT EXISTS(SELECT * FROM CartEntity WHERE pid = :productid)")
    Boolean is_exist(int productid);

    @Query("SELECT * FROM CartEntity")
    List<CartEntity> getAllCartEntry();

    @Query("DELETE FROM CartEntity WHERE pid = :id")
    void deleteById(int id);

    @Query("UPDATE CartEntity SET price = :p WHERE pid = :id")
    void updateById(int p,int id);

    @Query("UPDATE CartEntity SET qnt = :q WHERE pid = :id")
    void updateQuantityById(int q,int id);


}
