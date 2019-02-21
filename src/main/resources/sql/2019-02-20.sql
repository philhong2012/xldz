ALTER TABLE settlement_list ADD selling_income_date date NULL COMMENT '销售收入日期';
ALTER TABLE settlement_list ADD selling_income_date2 date NULL COMMENT '销售收入2日期';
ALTER TABLE settlement_list ADD selling_income_date3 date NULL COMMENT '销售收入3日期';
ALTER TABLE settlement_list ADD selling_income_date4 date NULL COMMENT '销售收入4日期';
ALTER TABLE settlement_list ADD selling_income_date5 date NULL COMMENT '销售收入5日期';


ALTER TABLE form_invoice ADD packing_mai_tou varchar(50) NULL COMMENT '装运口麦头';
ALTER TABLE packing_list ADD packing_mai_tou varchar(50) NULL COMMENT '装运口麦头';