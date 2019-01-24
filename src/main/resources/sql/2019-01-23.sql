ALTER TABLE settlement_list ADD pay_goods_cost decimal(15,2) NULL COMMENT '支出货款';

ALTER TABLE buying_contract_detail MODIFY price decimal(20,7) COMMENT '单价';

ALTER TABLE settlement_list ADD selling_income2 decimal(15,2) NULL COMMENT '销售收入2';ALTER TABLE settlement_list ADD selling_income2 decimal(15,2) NULL COMMENT '销售收入2';
ALTER TABLE settlement_list ADD selling_income3 decimal(15,2) NULL COMMENT '销售收入3';ALTER TABLE settlement_list ADD selling_income3 decimal(15,2) NULL COMMENT '销售收入3';
ALTER TABLE settlement_list ADD selling_income4 decimal(15,2) NULL COMMENT '销售收入4';ALTER TABLE settlement_list ADD selling_income4 decimal(15,2) NULL COMMENT '销售收入4';
ALTER TABLE settlement_list ADD selling_income5 decimal(15,2) NULL COMMENT '销售收入5';ALTER TABLE settlement_list ADD selling_income5 decimal(15,2) NULL COMMENT '销售收入5';
