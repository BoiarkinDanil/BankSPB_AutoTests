# language: ru
# @test
Функционал: Навигация по разделу Private Banking
  @smoke @web
  Сценарий: Навигация к разделу "Золото" в Private Banking
  Дано я открыл главную страницу БСПБ
  Когда я перехожу в раздел "Private Banking"
  И я кликаю на кнопку "ЗОЛОТО"
  Тогда я должен быть на странице "https://pb.bspb.ru/gold_2"