from py4j.java_gateway import JavaGateway

from recherche.stats_par_ensemble_de_mots import recherche_par_mots

class PythonProcessor:
    def __init__(self, gateway):
        self.gateway = gateway

    def test2(self):
        ArrayList = self.gateway.jvm.java.util.ArrayList
        Integer = self.gateway.jvm.java.lang.Integer

        lst2 = ArrayList()
        lst3 = ArrayList()

        for x in range(10000):
            lst2.add(Integer(123))
            lst2.add(Integer(124))

        for x in range(10000):
            lst3.add(Integer(2))
            lst3.add(Integer(3))

        lst = ArrayList()
        lst.add(lst2)
        lst.add(lst3)
        return lst

    def rechercheMots(self, mots):
        # types java
        HashMap = self.gateway.jvm.java.util.HashMap
        ArrayList = self.gateway.jvm.java.util.ArrayList
        Integer = self.gateway.jvm.java.lang.Integer
        Double = self.gateway.jvm.java.lang.Double
        String = self.gateway.jvm.java.lang.String

        # dictionnaire python en HashMap java
        java_result = HashMap()

        result = recherche_par_mots(mots)

        java_result.put("nb_word_used", Integer(result['nb_word_used']))
        java_result.put("number_lines_with_words", Integer(result['number_lines_with_words']))
        java_result.put("words_usage_ratio_by_line", Double(result['words_usage_ratio_by_line']))
        java_result.put("best_season_word_count", String(result['best_season_word_count']))

        # liste python en ArrayList java
        lines_list = ArrayList()
        for season, ep, character, line in result['lines_with_words']:
            entry = ArrayList()
            entry.add(String(season))
            entry.add(String(ep))
            entry.add(String(character))
            entry.add(String(line))
            lines_list.add(entry)
        java_result.put("lines_with_words", lines_list)

        character_list = ArrayList()
        for name, count in result['word_count_by_character']:
            entry = ArrayList()
            entry.add(String(name))
            entry.add(Integer(count))
            character_list.add(entry)
        java_result.put("word_count_by_character", character_list)

        season_list = ArrayList()
        for season, count, ratio in result['word_count_by_season']:
            entry = ArrayList()
            entry.add(String(season))
            entry.add(Integer(count))
            entry.add(Double(ratio))
            season_list.add(entry)
        java_result.put("word_count_by_season", season_list)

        episode_list = ArrayList()
        for season, ep, count, ratio in result['word_count_by_episode']:
            entry = ArrayList()
            entry.add(String(season))
            entry.add(String(ep))
            entry.add(Integer(count))
            entry.add(Double(ratio))
            episode_list.add(entry)
        java_result.put("word_count_by_episode", episode_list)

        # Dictionnaire python en HashMap java
        best_ep = HashMap()
        best_ep_data = result['best_episode_word_count']
        best_ep.put("season", String(best_ep_data['season']))
        best_ep.put("episode", String(best_ep_data['episode']))
        java_result.put("best_episode_word_count", best_ep)

        return java_result

    class Java:
        implements = ['controller.PythonProcessor']

gateway = JavaGateway(start_callback_server=True)
operator = PythonProcessor(gateway)
gateway.entry_point.register(operator)
#operator_example = gateway.jvm.com.mycompany.cours_ihm_version_propre.TestController2()
#numbers = operator_example.tt(operator)

input("Press Enter to exit")

