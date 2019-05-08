## 简介
该包主要是介绍如何使用`script`进行日期范围查询，分为`timestamp`和`date`。
在ES里面`timestamp`是设置为long类型的。

## 使用前需要在ES执行如下命令

### PUT`localhost:9200/date_test`
```json
{
	"mappings":{
		"a":{
			"properties":{
				"b":{
					"type":"date"
				},
				"c":{
					"type":"long"
				}
			}
		}
	}
}
```

### 插入数据(随意):
```json
{
	"b":"2014-05-18",
	"c":"1400373325000"
}
```