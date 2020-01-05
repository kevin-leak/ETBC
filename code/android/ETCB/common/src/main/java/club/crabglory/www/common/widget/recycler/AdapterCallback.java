package club.crabglory.www.common.widget.recycler;

interface AdapterCallback<Data> {
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
