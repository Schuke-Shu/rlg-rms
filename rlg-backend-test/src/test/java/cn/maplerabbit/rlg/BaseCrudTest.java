package cn.maplerabbit.rlg;

public interface BaseCrudTest
{
    void testSave();

    void testSaveBatch();

    void testRemove();

    void testRemoveBatch();

    void testUpdate();

    void testQuery();

    void testQueryBatch();

    void testCount();
}