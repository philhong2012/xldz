ALTER TABLE settlement_list ADD pay_goods_cost decimal(15,2) NULL COMMENT '支出货款';

ALTER TABLE buying_contract_detail MODIFY price decimal(20,7) COMMENT '单价';

