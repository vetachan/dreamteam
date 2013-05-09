import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConversionException;

public class DreamTeam {

    public static ImmutableMap<Type,ImmutableList<Integer>> effect;	
    public static ImmutableMap<Type,ImmutableList<Type>> types;
    public static Map<Type,Map<Type,Outcome>> initialResult; 
    public static ImmutableList<Pokemon> pokemons;

    public static void main(String[] args) {
        try {
            parseEffectivities(); 
            parseTypes();

            Team team = new Team();
            team.add(1, new Pokemon(Type.FLYING, Type.WATER));
            team.add(2, new Pokemon(Type.GHOST, Type.FLYING));
            //team.add(3, new Pokemon(Type.FIRE));
            //team.add(4, new Pokemon(Type.FIGHTING, Type.WATER));
            //team.add(5, new Pokemon(Type.DRAGON, Type.GROUND));
            //team.add(6, new Pokemon(Type.ELECTRIC));
            
            //team.analize();
            //team.analizeOutcome();
            
            //team.generateTeams();

            team.optimize();
                  
        } catch (ConfigurationException ce) {
            System.err.println("Could not open a configuration file");
        } catch (ConversionException ce) {
            System.err.println("Could not read configuration values");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Team {
        List<Pokemon> members;
        Map<Type,Map<Type,Outcome>> results;
        int score;

        public Team() {
            this.members = new ArrayList<Pokemon>();
            clearResults();
        }

        public Team(Team team) {
            this.members = new ArrayList<Pokemon>(team.members);
            this.score = team.score;
            this.results = new HashMap<Type,Map<Type,Outcome>>();
            for (Type key : team.results.keySet()) {
                this.results.put(key, new HashMap<Type,Outcome>(team.results.get(key)));
            }  
        }

        public void copy(Team team) {
            this.members = new ArrayList<Pokemon>(team.members);
            this.score = team.score;
            this.results = new HashMap<Type,Map<Type,Outcome>>();
            for (Type key : team.results.keySet()) {
                this.results.put(key, new HashMap<Type,Outcome>(team.results.get(key)));
            }  
        }

        public boolean equals(Team team) {
            if (this.members.size() != team.members.size()) return false;
            for (int i = 0; i < this.members.size(); ++i) {
                if (!this.members.get(i).equals(team.members.get(i))) return false;
            }
            return true;
        }

        public void clearResults() {
            score = 0;
            results = new HashMap<Type,Map<Type,Outcome>>();
            for (Type key : initialResult.keySet()) {
                results.put(key, new HashMap<Type,Outcome>(initialResult.get(key)));
            }    
        }

        public void add(int position, Pokemon pokemon) {
            members.add(position - 1, pokemon);
        }

        public void remove(int position) {
            members.remove(position - 1);
        }

        public Pokemon getMember(int position) {
            return members.get(position - 1);
        }

        public void analize() {           
            for (Pokemon member : members) {
                member.analize(results);
            }
        }

        public void analizeScore() {
            score = 0;
            for (Map.Entry<Type,Map<Type,Outcome>> entry : results.entrySet()) {
                for (Map.Entry<Type,Outcome> outcome : entry.getValue().entrySet()) {
                    score += outcome.getValue().value();
                }
            }
        }

        public void analizeOutcome() {
            System.out.println(members);
            score = 0;
            for (Map.Entry<Type,Map<Type,Outcome>> entry : results.entrySet()) {
                for (Map.Entry<Type,Outcome> outcome : entry.getValue().entrySet()) {
                    score += outcome.getValue().value();
                    Type type1 = entry.getKey();
                    Type type2 = outcome.getKey();
                    String types = type1.nom() + "/" + type2.nom();
                    switch (outcome.getValue()) {
                        case BAD: 
                            System.out.println("ALERT!!! Weakness against " + types);
                            break;
                        case EVEN:
                            System.out.println("Oops! Even against " + types);
                            break;
                        case FAIR:
                            System.out.println("Uhmm... Fair against " + types); 
                            break;
                        case GOOD:                            
                            System.out.println("Easy as a pie with " + types);
                            break;
                        case AMAZING:
                            System.out.println("Amazing against " + types + "!");
                            break;
                        default:
                            System.out.println("Unknown against " + types);
                    }
                }
            } 
            System.out.println("Score: " + score + " out of 347");         
        }

        // TODO Generate random first state and optimize
        // WARN Do not try to execute this without at least two fixed members!
        public void generateTeams() {
            Team team;
            Team bestTeam = null;
            List<Team> topTeams = new ArrayList<Team>();
            int n = pokemons.size();
            int nTeam = this.members.size();
            Pokemon member1, member2, member3, member4, member5, member6;

            for (int i1 = 0; i1 < n; ++i1) {
                member1 = nTeam > 0 ? this.getMember(1) : pokemons.get(i1);
                if (isInBlacklist(member1)) continue;

                for (int i2 = i1+1; i2 < n; ++i2) {  
                    member2 = nTeam > 1 ? this.getMember(2) : pokemons.get(i2);   
                    if (isInBlacklist(member2)) continue;

                    for (int i3 = i2+1; i3 < n; ++i3) {
                        member3 = nTeam > 2 ? this.getMember(3) : pokemons.get(i3);         
                        if (isInBlacklist(member3)) continue;

                        for (int i4 = i3+1; i4 < n; ++i4) {
                            member4 = nTeam > 3 ? this.getMember(4) : pokemons.get(i4);  
                            if (isInBlacklist(member4)) continue;

                            for (int i5 = i4+1; i5 < n; ++i5) {
                                member5 = nTeam > 4 ? this.getMember(5) : pokemons.get(i5); 
                                if (isInBlacklist(member5)) continue;

                                for (int i6 = i5+1; i6 < n; ++i6) {
                                    member6 = pokemons.get(i6);
                                    if (isInBlacklist(member6)) continue;

                                    team = new Team();
                                    team.add(1, member1);
                                    team.add(2, member2);
                                    team.add(3, member3);
                                    team.add(4, member4);
                                    team.add(5, member5);
                                    team.add(6, member6);

                                    team.analize();
                                    team.analizeScore();

                                    if (bestTeam == null) {
                                        bestTeam = new Team(team);
                                        topTeams.add(new Team(team));
                                    } else if (team.score > bestTeam.score) {                                        
                                        bestTeam = new Team(team);
                                        topTeams.clear();
                                        topTeams.add(new Team(team));
                                    } else if (team.score == bestTeam.score) {
                                        topTeams.add(new Team(team));
                                    }
                                }
                                if (nTeam > 4) i5 = n;
                            }
                            if (nTeam > 3) i4 = n;
                        }
                        if (nTeam > 2) i3 = n;
                    }
                    if (nTeam > 1) i2 = n;
                }
                if (nTeam > 0) i1 = n;
            }
                  
            for (Team topTeam : topTeams) {
                System.out.println(topTeam);
            }
            bestTeam.analizeOutcome();
        }

        // TODO Maybe improve to output best team possible
        public void optimize() {
            this.analize();
            this.analizeScore();

            int bestScore = this.score;
            int n = members.size();
            int r = (int)(Math.random() * n+1);
            for (int i = r; i < n + r; ++i) {
                this.optimizeMember(i % n + 1);
                if (this.score > bestScore) {
                    i = r-1;
                    bestScore = this.score;
                }
            }
            this.analizeOutcome();
        }

        public void optimizeMember(int i) {
            System.out.println("Optimizing member "  + i);
            List<Team> topTeams = new ArrayList<Team>();
            Team bestTeam = new Team(this);
            Team team = new Team(this);

            for (Pokemon poke : pokemons) {
                if (isInBlacklist(poke)) continue;
                team.clearResults();
                team.remove(i);
                team.add(i, poke);
                team.analize();
                team.analizeScore();
                if (team.score > bestTeam.score) {                                        
                    bestTeam = new Team(team);
                    topTeams.clear();
                    topTeams.add(new Team(team));
                } else if (team.score == bestTeam.score) {
                    topTeams.add(new Team(team));
                }
            }
   
            for (Team topTeam : topTeams) {
                if (!this.equals(topTeam)) {
                    System.out.println(topTeam);
                }
            }

            if (!this.equals(bestTeam)) {
                this.copy(bestTeam);
            }
        }

        public static boolean isInBlacklist(Pokemon poke) {
            // TODO Read from file
            if (poke.type1.equals(Type.DRAGON) && poke.type2.equals(Type.DRAGON)) return true;
            if (poke.type1.equals(Type.DRAGON) && poke.type2.equals(Type.GROUND)) return true;
            if (poke.type1.equals(Type.GHOST) && poke.type2.equals(Type.ELECTRIC)) return true;
            return false;
        }

        public String toString() {
            return score + ": " + members;
        }
    }

    public static class Pokemon {
        public Type type1;
        public Type type2;
        public boolean single;

        public Pokemon(Type type) {
            this.type1 = type;
            this.type2 = type;
            this.single = true;
        }

        public Pokemon(Type type1, Type type2) {
            this.type1 = type1;
            this.type2 = type2;
            this.single = type1.equals(type2);
        }

        public void analize(Map<Type,Map<Type,Outcome>> results) {
            Outcome bestOutcome;
            Outcome outcome;
            for (Pokemon foe : pokemons) {
                bestOutcome = results.get(foe.type1).get(foe.type2);
                if (bestOutcome.value() < Outcome.AMAZING.value()) {
                    outcome = this.analizeBattle(foe);
                    if (bestOutcome.value() < outcome.value()) {
                        results.get(foe.type1).put(foe.type2, outcome);
                    }
                }
            }
        }

        public Outcome analizeBattle(Pokemon foe) {
            int att = this.analizeAttack(foe);
            int def = foe.analizeAttack(this);
            if (att == 1 || def == 2) return Outcome.BAD;
            if (att == 0 && def == 0) return Outcome.EVEN;
            if (att == 2 && def == 1) return Outcome.AMAZING;
            if (def == 1) return Outcome.FAIR;
            if (att == 2) return Outcome.GOOD;
            return Outcome.GOOD;
        }

        public int analizeAttack(Pokemon foe) {
            int outcome = attack(this.type1, foe.type1);
            if (!this.single) {
                outcome = Math.max(outcome, attack(this.type2, foe.type1));
            }
            if (!foe.single) {
                outcome = Math.max(outcome, attack(this.type1, foe.type2));
            }
            if (!this.single && !foe.single) {
                outcome = Math.max(outcome, attack(this.type2, foe.type2));
            }
            return outcome;
        }

        public int attack(Type attack, Type defense) {
            if (attack == null || defense == null) return 0;
            int val = effect.get(attack).get(defense.id());
            return val;
        }

        public String toString() {
            if (single)
                return this.type1.nom();
            return this.type1.nom() + "/" + this.type2.nom();
        }

        public boolean equals(Pokemon poke) {
            if (this.type1.equals(poke.type1) && this.type2.equals(poke.type2)) {
                return true;
            }
            return false;
        }
    }

    public enum Outcome {
        AMAZING (4),
        GOOD    (3),
        FAIR    (2),
        EVEN    (1),
        BAD     (0);

        private final int value;
        Outcome(int value) {
            this.value = value;
        }
        public int value() { return value; }
    }

    public enum Type {
        NORMAL      (0, "normal"),
        FIRE        (1, "fire"),
        WATER       (2, "water"),
        ELECTRIC    (3, "electric"),
        GRASS       (4, "grass"),
        ICE         (5, "ice"),
        FIGHTING    (6, "fighting"),
        POISON      (7, "poison"),
        GROUND      (8, "ground"),
        FLYING      (9, "flying"),
        PSYCHIC     (10, "psychic"),
        BUG         (11, "bug"),
        ROCK        (12, "rock"),
        GHOST       (13, "ghost"),
        DRAGON      (14, "dragon"),
        DARK        (15, "dark"),
        STEEL       (16, "steel");

        private final int id;
        private final String nom;
        Type(int id, String nom) {
            this.id = id;
            this.nom = nom;
        }
        public int id() { return id; }
        public String nom() { return nom; }

        public static Type getType(String nom) {
            if (nom != null) {
                for (Type t : Type.values()) {
                    if (nom.equalsIgnoreCase(t.nom)) {
                       return t;
                    }
                }
            }
            throw new IllegalArgumentException("No type with name " + nom + " found");
        }
    }

    public static void parseEffectivities() throws ConfigurationException {
        System.out.print("Parsing effectivities... ");
        Configuration config = new PropertiesConfiguration("effect.properties");
        ImmutableMap.Builder<Type,ImmutableList<Integer>> builder = new ImmutableMap.Builder<Type,ImmutableList<Integer>>();
        
        Iterator it = config.getKeys();
        while (it.hasNext()) {
            String key = it.next().toString();         
            try {
                Type type = Type.getType(key);
                builder.put(type, parseEffectivity(config, key));
            } catch (IllegalArgumentException iae) {
                System.err.println("Ignoring missing effectivity type " + key);
            }
        }

        effect = builder.build();
        System.out.println("OK");
    }

    public static ImmutableList<Integer> parseEffectivity(Configuration config, String key) {
        ImmutableList.Builder<Integer> builder = new ImmutableList.Builder<Integer>();
        for (Object o : config.getList(key)) {
            try {
                builder.add(Integer.valueOf((String) o));    
            } catch (NumberFormatException nfe) {
                System.err.println("Replacing missing effectivity with neutral");
                builder.add(0);
            }
        }
        return builder.build();
    }


    public static void parseTypes() throws ConfigurationException {
        System.out.print("Parsing types... ");
        Configuration config = new PropertiesConfiguration("types.properties");

        ImmutableList.Builder<Pokemon> builder = new ImmutableList.Builder<Pokemon>(); 
        initialResult = new HashMap<Type,Map<Type,Outcome>>();

        Iterator it = config.getKeys();
        while (it.hasNext()) {
            String key = it.next().toString();
            try {
                Type type = Type.getType(key);
                builder.addAll(parseType(config, key, type));          
            } catch (IllegalArgumentException iae) {
                System.err.println("Ignoring missing primary type " + key);
            }
        }
        pokemons = builder.build();
        System.out.println("OK");
    }

    public static List<Pokemon> parseType(Configuration config, String key1, Type type1) {
        List<Pokemon> pokes = new ArrayList<Pokemon>();
        Map<Type,Outcome> resultMap = new HashMap<Type,Outcome>();    
        
        for (Object o : config.getList(key1)) {
            String key2 = o.toString();
            try {
                Type type2 = Type.getType(key2);
                if (initialResult != null && initialResult.containsKey(type2)
                    && initialResult.get(type2).containsKey(type1)) continue;
                pokes.add(new Pokemon(type1, type2));
                resultMap.put(type2, Outcome.BAD);
            } catch (IllegalArgumentException iae) {
                System.err.println("Ignoring missing type " + key2);
            }
        }

        initialResult.put(type1, resultMap);
        return pokes;
    }

}
